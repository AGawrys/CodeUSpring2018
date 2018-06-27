

<%@page import="codeu.model.store.basic.ConversationStore"%>
<%@page import="java.time.ZoneId"%>
<%@page import="java.util.Locale"%>
<%@page import="java.time.format.FormatStyle"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="codeu.model.data.User"%>
<%@page import="codeu.model.store.basic.UserStore"%>
<%@page import="java.time.Instant"%>
<%@page import="java.util.UUID"%>
<%@page import="codeu.model.data.Message"%>
<%@page import="codeu.model.data.Mention"%>
<%@page import="codeu.model.store.basic.MessageStore" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mentions</title>
    </head>
    <body>
       <%!
            String messageSent(Mention mention) {
                    String msg = formatMessage(mention.objectId);
                    return msg;
                }

            String formatMessage(UUID msgId) {
                String msg;
                MessageStore messageStore = MessageStore.getInstance();
                Message message = messageStore.getById(msgId);
                String username = formatUsername(message.getAuthorId());
                String time = formatCreationTime(message.getCreationTime());
                String convoName = formatConversation(message.getConversationId());
                if (message.getContent().matches("@maria")){
                  msg = time + " " + username + " sent a message in " + convoName + "chat: " + message.getContent();
                  return msg;
                }
            }

            String formatCreationTime(Instant time) {
                DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(Locale.US).withZone(ZoneId.systemDefault());
                String result = formatter.format(time);
                return result;
            }

            String formatUsername(UUID userId) {
                UserStore userStore = UserStore.getInstance();
                String username = userStore.getUser(userId).getName();
                return username;
            }

            String formatConversation(UUID convoId) {
                ConversationStore convoStore = ConversationStore.getInstance();
                String convo = convoStore.getById(convoId).getTitle();
                return convo;
            }
        %>
    </body>
</html>
