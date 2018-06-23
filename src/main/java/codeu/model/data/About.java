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

package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/**
 * Class representing a About, which can be thought of as a chat room. Abouts are
 * created by a User and contain Messages.
 */
public class About {
  public final UUID id;
  public final UUID owner;
  public final Instant creation;
  public final String title;
  public final String image;

  /**
   * Constructs a new About.
   *
   * @param id the ID of this About
   * @param owner the ID of the User who created this About
   * @param title the title of this About
   * @param creation the creation time of this About
   */
  public About(UUID id, UUID owner, String title, Instant creation, String image) {
    this.id = id;
    this.owner = owner;
    this.creation = creation;
    this.title = title;
    this.image = image;
  }

  /** Returns the ID of this About. */
  public UUID getId() {
    return id;
  }

  /** Returns the ID of the User who created this About. */
  public UUID getOwnerId() {
    return owner;
  }

  /** Returns the title of this About. */
  public String getTitle() {
    return title;
  }

  /** Returns the creation time of this About. */
  public Instant getCreationTime() {
    return creation;
  }
  
  public String getImage(){
      return image;
  }
}