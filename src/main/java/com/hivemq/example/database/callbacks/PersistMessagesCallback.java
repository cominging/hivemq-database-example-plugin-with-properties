package com.hivemq.example.database.callbacks;

import com.hivemq.spi.callback.CallbackPriority;
import com.hivemq.spi.callback.events.OnPublishReceivedCallback;
import com.hivemq.spi.callback.exception.OnPublishReceivedException;
import com.hivemq.spi.message.PUBLISH;
import com.hivemq.spi.security.ClientData;
import com.hivemq.spi.services.PluginExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Dominik Obermaier
 * @author Florian Limpöck
 */
public class PersistMessagesCallback implements OnPublishReceivedCallback {

    private static Logger log = LoggerFactory.getLogger(PersistMessagesCallback.class);
    private final Provider<Connection> connectionProvider;
    private final PluginExecutorService pluginExecutorService;

    private static final String SQLStatement = "INSERT INTO `messages` (client_id,topic,message,qos,receive_time) VALUES (?,?,?,?,NOW())";


    @Inject
    public PersistMessagesCallback(final Provider<Connection> connectionProvider,
                                   final PluginExecutorService pluginExecutorService) {
        this.connectionProvider = connectionProvider;
        this.pluginExecutorService = pluginExecutorService;
    }

    @Override
    public void onPublishReceived(final PUBLISH publish, final ClientData clientData) throws OnPublishReceivedException {
        final Connection connection = connectionProvider.get();

        pluginExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final PreparedStatement preparedStatement = connection.prepareStatement(SQLStatement);
                    preparedStatement.setString(1, clientData.getClientId());
                    preparedStatement.setString(2, publish.getTopic());
                    preparedStatement.setString(3, new String(publish.getPayload()));
                    preparedStatement.setInt(4, publish.getQoS().getQosNumber());

                    preparedStatement.execute();
                    preparedStatement.close();
                } catch (SQLException e) {
                    log.error("An error occured while preparing the SQL statement", e);
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        log.error("An error occured while giving back a connection to the connection pool");
                    }
                }
            }
        });

    }

    @Override
    public int priority() {
        return CallbackPriority.HIGH;
    }
}
