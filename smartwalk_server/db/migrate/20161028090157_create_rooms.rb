class CreateRooms < ActiveRecord::Migration[5.0]
  def change
    create_table :rooms do |t|
      t.string :room_id

      t.timestamps
    end

    add_index :rooms, :room_id, :unique => true
  end
end
