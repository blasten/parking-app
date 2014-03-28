class User < ActiveRecord::Base
  # ROLES
  ROLE_MOBILE = 1
  ROLE_ADMIN = 2

  # Validations
  validates :email, presence: true, uniqueness: true, format: { with: /\A[a-zA-Z0-9_\.-]{1,}@[a-zA-Z0-9_\.-]{1,}\.[a-zA-Z0-9_-]{2,}\z/, message: "Invalid email format" }
  validates :name, presence: true, length: { within: 2..10 }
  validates :password, presence: true, length: { within: 4..20 }

  before_save { |user| user.email = email.downcase.strip }
  before_save{ |user| user.role ||= ROLE_MOBILE}


  def is_mobile_user?
    read_attribute(:role) == ROLE_MOBILE
  end

  def is_admin_user?
    read_attribute(:role) == ROLE_ADMIN
  end

  def role_to_string
    if is_mobile_user?
      "mobile"
    elsif is_admin_user?
      "admin"
    else
      ""
    end
  end

  def to_hash
    hash = {}
    self.attributes.each { |k,v| hash[k] = v }
    hash["role"] = role_to_string
    hash.delete("password")
    hash
  end
end
