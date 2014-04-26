class User < ActiveRecord::Base
  has_many :reservations
  has_many :reports

  # ROLES
  ROLE_MOBILE_STAFF = 1
  ROLE_MOBILE_STUDENT = 3
  ROLE_MOBILE_VISITOR = 4

  ROLE_ADMIN = 2

  ROLES = {
    ROLE_MOBILE_STAFF => "Mobile user - Staff",
    ROLE_MOBILE_STUDENT => "Mobile user - Student",
    ROLE_MOBILE_VISITOR => "Mobile user - Visitor",
    ROLE_ADMIN => "Admin user"
  }

  # Validations
  validates :email, presence: true, uniqueness: true, format: { with: /\A[a-zA-Z0-9_\.-]{1,}@[a-zA-Z0-9_\.-]{1,}\.[a-zA-Z0-9_-]{2,}\z/, message: "Invalid email format" }
  validates :name, presence: true, length: { within: 1..10 }
  validates :password, presence: true, length: { within: 4..20 }

  before_save { |user| 
    user.email = email.downcase.strip
    user.role ||= ROLE_MOBILE
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
      "mobile-staff"
    elsif is_student?
      "mobile-student"
    elsif is_visitor?
      "mobile-visitor"
    elsif is_admin_user?
      "admin"
    else
      "unknown"
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

  private
    def create_remember_token
      if self.remember_token.nil? || self.remember_token.empty?
        self.remember_token = SecureRandom.urlsafe_base64
      end
    end
end
