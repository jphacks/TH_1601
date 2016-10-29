class AddFriendTokenToUser < ActiveRecord::Migration[5.0]
  def change
    add_column :users, :friend_token, :string
    add_index :users, :friend_token, :unique => true
  end
end
