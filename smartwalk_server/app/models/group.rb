class Group < ApplicationRecord
  validates :group_id, presence: true
  validates :group_id, uniqueness: true
end
