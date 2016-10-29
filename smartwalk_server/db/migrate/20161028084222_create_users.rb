class CreateUsers < ActiveRecord::Migration[5.0]
  def change
    create_table :users do |t|
      t.string :user_id, :null => false
      t.text :display_name
      t.text :picture_url
      t.text :status_message

      t.timestamps
    end

    add_index :users, :user_id, :unique => true
    add_index :users, :display_name
  end
end
