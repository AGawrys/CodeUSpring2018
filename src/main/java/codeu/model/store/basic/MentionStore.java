/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeu.model.store.basic;

import codeu.model.store.basic.UserStore;
import codeu.model.data.Mention;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import static codeu.model.data.Type.CONVERSATIONSTART;
import static codeu.model.data.Type.MESSAGESENT;
import static codeu.model.data.Type.USERJOINED;
import codeu.model.data.User;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MentionStore {

    private static MentionStore instance;
    /** Store class that gives access to Conversations. */
    private ConversationStore conversationStore;
    /** Store class that gives access to Messages. */
    private MessageStore messageStore;
    /** Store class that gives access to Users. */
    private UserStore userStore;

    public static MentionStore getInstance() {
        if (instance == null) {
          instance = new MentionStore();
        }
        return instance;
    }

    private List<Message> messages;
    private List<Conversation> conversations;
    private List<User> users;

    private MentionStore() {
        setMessageStore(MessageStore.getInstance());
        setConversationStore(ConversationStore.getInstance());
        setUserStore(UserStore.getInstance());
    }

    //method should sort from most recent to least recent, only a chunk of data
    public List<Mention> getAllMentions() {
        messages = messageStore.getAll();
        conversations = conversationStore.getAllConversations();
        users = userStore.getAll();
        String currentUser = "maria";
        List<Mention> mentions = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
          if(messages.get(i).getContent().matches("(.*)@" + currentUser + "(.*)")){
        mentions.add(new Mention(MESSAGESENT, messages.get(i).getId(), messages.get(i).getCreationTime()));
        }
        }

        mentions.sort((Mention a1, Mention a2) -> a1.getCreationTime().compareTo(a2.getCreationTime()));
        Collections.reverse(mentions);
        return mentions;
    }

    public void setMessageStore(MessageStore messageStore) {
        this.messageStore = messageStore;
    }
    public void setConversationStore(ConversationStore conversationStore) {
        this.conversationStore = conversationStore;
    }
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

}
