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

package codeu.model.store.basic;

import codeu.model.data.About;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class AboutMeStore {

  /** Singleton instance of AboutMeStore. */
  private static AboutMeStore instance;

  /**
   * Returns the singleton instance of AboutMeStore that should be shared between all servlet
   * classes. Do not call this function from a test; use getTestInstance() instead.
   */
  public static AboutMeStore getInstance() {
    if (instance == null) {
      instance = new AboutMeStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static AboutMeStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new AboutMeStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading abouts from and saving abouts
   * to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of abouts. */
  private List<About> abouts;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private AboutMeStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    abouts = new ArrayList<>();
  }

/** Access the current set of abouts known to the application. */
  public List<About> getAllabouts() {
    return abouts;
  }

  /** Add a new about to the current set of abouts known to the application. */
  public void addabout(About about) {
    abouts.add(about);
    persistentStorageAgent.writeThrough(about);
  }

  /** Check whether a about title is already known to the application. */
  public boolean isTitleTaken(String title) {
    // This approach will be pretty slow if we have many abouts.
    for (About about : abouts) {
      if (about.getTitle().equals(title)) {
        return true;
      }
    }
    return false;
  }

  /** Find and return the about with the given title. */
  public About getaboutWithTitle(String title) {
    for (About about : abouts) {
      if (about.getTitle().equals(title)) {
        return about;
      }
    }
    return null;
  }

  /** Sets the List of abouts stored by this AboutMeStore. */
  public void setabouts(List<About> abouts) {
    this.abouts = abouts;
  }
}
