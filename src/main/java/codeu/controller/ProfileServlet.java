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

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.data.About;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.AboutMeStore;
import com.google.appengine.repackaged.com.google.api.client.util.Base64;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
/** Servlet class responsible for the login page. */
@MultipartConfig(maxFileSize=10*1024*1024)
public class ProfileServlet extends HttpServlet {

    /**
     * Store class that gives access to Users.
     */
    private UserStore userStore;

    private AboutMeStore aboutMeStore;

    /**
     * Set up state for handling login-related requests. This method is only
     * called when running in a server, not when running in a test.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        setUserStore(UserStore.getInstance());
        setAboutMeStore(AboutMeStore.getInstance());

    }

    /**
     * Sets the UserStore used by this servlet. This function provides a common
     * setup method for use by the test framework or the servlet's init()
     * function.
     */
    void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    void setAboutMeStore(AboutMeStore aboutMeStore) {
        this.aboutMeStore = aboutMeStore;
    }

    /**
     * This function fires when a user requests the /profile URL. It simply
     * forwards the request to profile.jsp.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String requestUrl = request.getRequestURI();
        String userProfile = requestUrl.substring("/user/".length());
        User user = userStore.getUser(userProfile);
        if (user == null) {
            // couldn't find conversation, redirect to conversation list
            System.out.println("Couldn't find " + userProfile);
            response.sendRedirect("/login");
            return;
        }
        String aboutString = request.getParameter("AboutMe");

        About about = aboutMeStore.getaboutWithTitle(aboutString);

        request.setAttribute("userProfile", userProfile);
        request.setAttribute("user", user);
        request.setAttribute("AboutMe", about);

        if (user.getImageString() == null) {
            request.setAttribute("image", "No Profile Picture");
        } else {
            request.setAttribute("image", user.getImageString());
        }

        request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
    }

    /**
     * This function fires when a user submits the login form. It gets the
     * username and password from the submitted form data, checks for validity
     * and if correct adds the username to the session so we know the user is
     * logged in.
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String username = (String) request.getSession().getAttribute("user");
        if (username == null) {

            response.sendRedirect("/login");
            return;
        }

        String requestUrl = request.getRequestURI();

        Part filePart = request.getPart("file");
        InputStream fileContent = filePart.getInputStream();
        String imageString = encodeString(fileContent);

        String aboutString = request.getParameter("AboutMe");
        About aboutID = aboutMeStore.getaboutWithTitle(aboutString);

        User user = userStore.getUser(username);
        user.setImageString(imageString);
        if (user == null) {
            response.sendRedirect("/login");
            return;
        }
        if (aboutString != null) {

            About about
                    = new About(
                            UUID.randomUUID(),
                            user.getId(),
                            aboutString,
                            Instant.now());

            aboutMeStore.addabout(about);
        }
        response.sendRedirect("/user/" + username);
    }

    private String encodeString(InputStream fileInputStreamReader) {
        String resultEncoded = null;
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int zero = 0;
            while ((zero = fileInputStreamReader.read(buffer, 0, buffer.length)) != -1) {
                output.write(buffer, 0, zero);
            }
            output.flush();

            byte[] bytes = output.toByteArray();
            fileInputStreamReader.read(bytes);
            resultEncoded = new String(Base64.encodeBase64(bytes), "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultEncoded;
    }
}
