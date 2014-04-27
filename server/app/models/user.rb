class User < ActiveRecord::Base
  has_many :reservations
  has_many :reports

  # ROLES
  ROLE_MOBILE_STAFF = 1
  ROLE_MOBILE_STUDENT = 3
  ROLE_MOBILE_VISITOR = 4

  ROLE_ADMIN = 2

  # Is it allowed to create an admin user?

  @@admin_permission = false

  ROLES = {
    ROLE_MOBILE_STAFF => "Staff",
    ROLE_MOBILE_STUDENT => "Student",
    ROLE_MOBILE_VISITOR => "Visitor",
    ROLE_ADMIN => "Admin"
  }

  # Validations
  validates :email, presence: true, uniqueness: true, format: { with: /\A[a-zA-Z0-9_\.-]{1,}@[a-zA-Z0-9_\.-]{1,}\.[a-zA-Z0-9_-]{2,}\z/, message: "Invalid email format" }
  validates :name, presence: true, length: { within: 1..10 }
  validates :password, presence: true, length: { within: 4..20 }
  validate :verify_role

  before_save { |user| 
    user.email = email.downcase.strip
    user.role ||= ROLE_MOBILE_STUDENT
  }

  before_save :create_remember_token

  def is_mobile_user?
    is_staff? || is_student? || is_visitor?
  end

  def is_admin_user?
    read_attribute(:role) == ROLE_ADMIN
  end

  def is_staff?
    read_attribute(:role) == ROLE_MOBILE_STAFF
  end

  def is_student?
    read_attribute(:role) == ROLE_MOBILE_STUDENT
  end

  def is_visitor?
    read_attribute(:role) == ROLE_MOBILE_VISITOR
  end

  def role_to_s
    if is_staff?
      "staff"
    elsif is_student?
      "student"
    elsif is_visitor?
      "visitor"
    elsif is_admin_user?
      "admin"
    else
      "unknown"
    end
  end

  def self.get_role(role)
    if role == "staff"
      ROLE_MOBILE_STAFF
    elsif role == "student"
      ROLE_MOBILE_STUDENT
    elsif role == "visitor"
      ROLE_MOBILE_VISITOR
    elsif role == "admin"
      ROLE_ADMIN
    end
  end

  def to_hash
    hash = attributes.to_hash
    hash["role"] = role_to_s
    hash.delete("password")
    hash
  end

  def self.find_admin_user(email, password)
    # user.authenticate(password)
    User.where('email = ? and password = ? and role = ?', email, password, ROLE_ADMIN).first
  end

  def self.admin_permission(ok)
    @@admin_permission = ok
  end

  private
    def create_remember_token
      if self.remember_token.nil? || self.remember_token.empty?
        self.remember_token = SecureRandom.urlsafe_base64
      end
    end

    def verify_role
      if role != ROLE_MOBILE_STAFF && role != ROLE_MOBILE_STUDENT && role != ROLE_MOBILE_VISITOR && !(role == ROLE_ADMIN && @@admin_permission)
        errors.add(:role, "is not allowed")
      end
    end
end
