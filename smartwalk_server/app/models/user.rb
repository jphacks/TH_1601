class User < ApplicationRecord
  validates :user_id, presence: true
  validates :user_id, uniqueness: true
  validates :display_name, presence: true

  has_many :registration_tokens, dependent: :destroy
  has_and_belongs_to_many :groups
  has_and_belongs_to_many :rooms

  def search_user(sender, displayName)
    User.find_by(user_id: sender)
  end
end
