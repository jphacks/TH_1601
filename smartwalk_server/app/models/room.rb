class Room < ApplicationRecord
  validates :room_id, presence: true
  validates :room_id, uniqueness: true

  has_and_belongs_to_many :users
end
