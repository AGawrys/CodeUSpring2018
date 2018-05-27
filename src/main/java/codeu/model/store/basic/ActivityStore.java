/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeu.model.store.basic;

import codeu.model.data.Activity;
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

/**
 *
 * @author Agnieszka
 */
public class ActivityStore {
    
    private static ActivityStore instance;
    /** Store class that gives access to Conversations. */
    private ConversationStore conversationStore;
    /** Store class that gives access to Messages. */
    private MessageStore messageStore;
    /** Store class that gives access to Users. */
    private UserStore userStore;
    
    public static ActivityStore getInstance() {
        if (instance == null) {
          instance = new ActivityStore();
        }
        return instance;
    }
    
    private List<Message> messages;
    private List<Conversation> conversations;
    private List<User> users;
    
    private ActivityStore() {
        setMessageStore(MessageStore.getInstance());
        setConversationStore(ConversationStore.getInstance());
        setUserStore(UserStore.getInstance());
    }
    
    //method should sort from most recent to least recent, only a chunk of data
    public List<Activity> getAllActivities() {
        messages = messageStore.getAll();
        conversations = conversationStore.getAllConversations();
        users = userStore.getAll();
        List<Activity> activities = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            activities.add(new Activity(MESSAGESENT, messages.get(i).getId(), messages.get(i).getCreationTime()));
        }
        for (int j = 0; j < conversations.size(); j++) {
            activities.add(new Activity(CONVERSATIONSTART, conversations.get(j).getId(), conversations.get(j).getCreationTime()));
        }
        for (int k = 0; k < users.size(); k++) {
            activities.add(new Activity(USERJOINED, users.get(k).getId(), users.get(k).getCreationTime()));
        }
        activities.sort((Activity a1, Activity a2) -> a1.getCreationTime().compareTo(a2.getCreationTime()));
        Collections.reverse(activities);
        return activities;
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
