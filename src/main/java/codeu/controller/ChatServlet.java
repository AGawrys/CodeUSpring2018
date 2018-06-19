// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;
import com.vdurmont.emoji.EmojiParser;
import java.io.FileWriter;
import java.io.IOException;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.jsoup.safety.Cleaner;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.nodes.Document;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.image.BufferedImage;
import java.awt.*;


/** Servlet class responsible for the chat page. */
public class ChatServlet extends HttpServlet {

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;

  /** Store class that gives access to Messages. */
  private MessageStore messageStore;

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Set up state for handling chat requests. */
  @Override
  public void init() throws ServletException {
    super.init();
    setConversationStore(ConversationStore.getInstance());
    setMessageStore(MessageStore.getInstance());
    setUserStore(UserStore.getInstance());
  }
  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  /**
   * Sets the MessageStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * This function fires when a user navigates to the chat page. It gets the conversation title from
   * the URL, finds the corresponding Conversation, and fetches the messages in that Conversation.
   * It then forwards to chat.jsp for rendering.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String requestUrl = request.getRequestURI();
    String conversationTitle = requestUrl.substring("/chat/".length());

    Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);
    if (conversation == null) {
      // couldn't find conversation, redirect to conversation list
      System.out.println("Conversation was null: " + conversationTitle);
      response.sendRedirect("/conversations");
      return;
    }

    UUID conversationId = conversation.getId();

    List<Message> messages = messageStore.getMessagesInConversation(conversationId);

    request.setAttribute("conversation", conversation);
    request.setAttribute("messages", messages);
    request.getRequestDispatcher("/WEB-INF/view/chat.jsp").forward(request, response);
  }
    //Method to clean the user's text
    public static String clean (String messageToClean, Whitelist whitelist){
      Document dirty = Parser.parseBodyFragment(messageToClean, "");
      Cleaner cleaner = new Cleaner(Whitelist.basicWithImages().addTags("strike", "code"));
      Document clean = cleaner.clean(dirty);
      clean.outputSettings().prettyPrint(false);
      return clean.body().html();
    }

  /**
   * This function fires when a user submits the form on the chat page. It gets the logged-in
   * username from the session, the conversation title from the URL, and the chat message from the
   * submitted form data. It creates a new Message from that data, adds it to the model, and then
   * redirects back to the chat page.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {


    //AutoreplaceSmiles autoreplace = new AutoreplaceSmiles();

    String username = (String) request.getSession().getAttribute("user");
    if (username == null) {
      // user is not logged in, don't let them add a message
      response.sendRedirect("/login");
      return;
    }

    User user = userStore.getUser(username);
    if (user == null) {
      // user was not found, don't let them add a message
      response.sendRedirect("/login");
      return;
    }

    String requestUrl = request.getRequestURI();
    String conversationTitle = requestUrl.substring("/chat/".length());

    Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);
    if (conversation == null) {
      // couldn't find conversation, redirect to conversation list
      response.sendRedirect("/conversations");
      return;
    }

    String messageContent = request.getParameter("message");

    //String messageContent = EmojiParser.parseToUnicode(messageContent1);

    // this removes any HTML from the message content
    String cleanedMessageContent = clean(messageContent, Whitelist.basicWithImages());
    String cleanedAndEmojiMessage = EmojiParser.parseToUnicode(cleanedMessageContent);

    Message message =
        new Message(
            UUID.randomUUID(),
            conversation.getId(),
            user.getId(),
            cleanedAndEmojiMessage,
            Instant.now());

    messageStore.addMessage(message);
  
    // redirect to a GET request
    response.sendRedirect("/chat/" + conversationTitle);
  }









  public class AutoreplaceSmiles extends JEditorPane {
    ImageIcon SMILE_IMG=createImage();

    public void main(String[] args) {
        JFrame frame = new JFrame("Autoreplace :) with Smiles images example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final AutoreplaceSmiles app = new AutoreplaceSmiles();
        app.setEditorKit(new StyledEditorKit());
        app.initListener();
        JScrollPane scroll = new JScrollPane(app);
        frame.getContentPane().add(scroll);

        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public AutoreplaceSmiles() {
        super();
    }

    private void initListener() {
        getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent event) {
                final DocumentEvent e=event;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (e.getDocument() instanceof StyledDocument) {
                            try {
                                StyledDocument doc=(StyledDocument)e.getDocument();
                                int start= Utilities.getRowStart(AutoreplaceSmiles.this,Math.max(0,e.getOffset()-1));
                                int end=Utilities.getWordStart(AutoreplaceSmiles.this,e.getOffset()+e.getLength());
                                String text=doc.getText(start, end-start);

                                int i=text.indexOf(":)");
                                while(i>=0) {
                                    final SimpleAttributeSet attrs=new SimpleAttributeSet(
                                       doc.getCharacterElement(start+i).getAttributes());
                                    if (StyleConstants.getIcon(attrs)==null) {
                                        StyleConstants.setIcon(attrs, SMILE_IMG);
                                        doc.remove(start+i, 2);
                                        doc.insertString(start+i,":)", attrs);
                                    }
                                    i=text.indexOf(":)", i+2);
                                }
                            } catch (BadLocationException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
            }
            public void removeUpdate(DocumentEvent e) {
            }
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    ImageIcon createImage() {
        BufferedImage res=new BufferedImage(17, 17, BufferedImage.TYPE_INT_ARGB);
        Graphics g=res.getGraphics();
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.yellow);
        g.fillOval(0,0,16,16);

        g.setColor(Color.black);
        g.drawOval(0,0,16,16);

        g.drawLine(4,5, 6,5);
        g.drawLine(4,6, 6,6);

        g.drawLine(11,5, 9,5);
        g.drawLine(11,6, 9,6);

        g.drawLine(4,10, 8,12);
        g.drawLine(8,12, 12,10);
        g.dispose();

        return new ImageIcon(res);
    }
}
}
