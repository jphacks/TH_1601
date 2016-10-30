class Group < ApplicationRecord
  validates :group_id, presence: true
  validates :group_id, uniqueness: true

  has_and_belongs_to_many :users
end
