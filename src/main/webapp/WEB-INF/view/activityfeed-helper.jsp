<%-- 
    Document   : activityfeed-helper
    Created on : May 26, 2018, 3:57:13 PM
    Author     : Agnieszka
--%>

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
<%@page import="codeu.model.data.Activity"%>
<%@page import="codeu.model.store.basic.MessageStore" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <%!               
            String messageSent(Activity activity){
                String msg = formatMessage(activity.objectId);
                return msg;
            }
            String userJoined(Activity activity){
                String username = formatUsername(activity.getObjectId());
                String time = formatCreationTime(activity.getCreationTime());
                String result = time + " " + username + " joined!";
                return result;
            }
            String conversationStarted(Activity activity){
                ConversationStore convoStore = ConversationStore.getInstance();
                String convo = formatConversation(activity.getObjectId());
                String username = formatUsername(convoStore.getById(activity.getObjectId()).getOwnerId());
                String time = formatCreationTime(convoStore.getById(activity.getObjectId()).getCreationTime());
                String result = time + " " + username + " started a new conversation: " + convo;
                return result;
            }
            String formatMessage(UUID msgId){
               String msg;
               MessageStore messageStore = MessageStore.getInstance();
               Message message = messageStore.getById(msgId);
               String username = formatUsername(message.getAuthorId());
               String time = formatCreationTime(message.getCreationTime());
               String convoName = formatConversation(message.getConversationId());
               msg = time +" " + username + " sent a message in " + convoName + "chat: " + message.getContent();
               return msg;
            }
            String formatCreationTime(Instant time){
                DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(Locale.US).withZone(ZoneId.systemDefault());
                String result = formatter.format(time);
                return result;
            }
            String formatUsername(UUID userId){
               UserStore userStore = UserStore.getInstance();
               String username = userStore.getUser(userId).getName();
               return username;
            }
            String formatConversation(UUID convoId){
               ConversationStore convoStore = ConversationStore.getInstance();
               String convo = convoStore.getById(convoId).getTitle();
               return convo;
            }
        %>
    </body>
</html>
