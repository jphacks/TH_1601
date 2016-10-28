class CreateGroups < ActiveRecord::Migration[5.0]
  def change
    create_table :groups do |t|
      t.string :group_id

      t.timestamps
    end

    add_index :groups, :group_id, :unique => true
  end
end
