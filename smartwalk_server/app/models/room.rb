class Room < ApplicationRecord
  validates :room_id, presence: true
  validates :room_id, uniqueness: true
end
