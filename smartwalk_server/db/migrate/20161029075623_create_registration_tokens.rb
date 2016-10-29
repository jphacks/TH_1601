class CreateRegistrationTokens < ActiveRecord::Migration[5.0]
  def change
    create_table :registration_tokens do |t|
      t.string :token, :null => false
      t.references :user, :null => false

      t.timestamps
    end

    add_index :registration_tokens, :token, :unique => true
  end
end
