class AddMidToUsers < ActiveRecord::Migration[5.0]
  def change
    add_column :users, :mid, :string
    add_index :users, :mid, :unique => true
  end
end
