class CreateRoomJoinTable < ActiveRecord::Migration[5.0]
  def change
    create_join_table :rooms, :users do |t|
      # t.index [:room_id, :user_id]
      t.index [:user_id, :room_id], unique: true
    end
  end
end
