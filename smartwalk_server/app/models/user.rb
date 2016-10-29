class User < ApplicationRecord
  validates :user_id, presence: true
  validates :user_id, uniqueness: true
  validates :display_name, presence: true
end
