class User < ApplicationRecord
  validates :user_id, presence: true
  validates :user_id, uniqueness: true
  validates :display_name, presence: true

  has_many :registration_tokens, dependent: :destroy
  has_and_belongs_to_many :groups
  has_and_belongs_to_many :rooms
  has_and_belongs_to_many :friends, autosave: true, class_name: "User", join_table: :friendships,
                          foreign_key: 'user_id', association_foreign_key: :friend_user_id

  def self.generate_random_string
    domain =  [('a'..'z'), ('A'..'Z'), ('0'..'9')].map{|i| i.to_a}.flatten
    (0...24).map{domain.to_a[rand(domain.length)] }.join
  end

  def self.make_friend(user1, user2)
    begin
      User.transaction do
        raise "user cannot be the same item." if user1 == user2
        user1.friends << user2
        user2.friends << user1
        user1.save!
        user2.save!
      end
      true
    rescue
      false
    end
  end

  def friend_url()
    routes = Rails.application.routes.url_helpers
    routes.url_for host: 'smartwalk.stonedot.com', controller: 'users', action: 'friend', id: friend_token
  end

  def friend_api_url()
    "smart-walk://friend/" + friend_token
  end

  def search_user(sender, displayName)
    User.find_by(user_id: sender)
  end
end
