class Lot < ActiveRecord::Base
  has_many :spots
  has_many :reports

  # ROLES
  DESIGNATION_FACULTY = 1
  DESIGNATION_STUDENTS = 2
  DESIGNATION_VISITORS = 3
  DESIGNATION_ALL = 4

  # Default latitude and longitude
  DEFAULT_LATITUDE = 39.031619
  DEFAULT_LONGITUDE = -84.464135

  DESIGNATIONS = {
    DESIGNATION_ALL => "Any",
    DESIGNATION_FACULTY => "Faculty/Staff",
    DESIGNATION_STUDENTS => "Students",
    DESIGNATION_VISITORS => "Visitors"
  }

  validates :name, presence: true, length: { within: 2..30 }
  #validates :max_wait, numericality: {only_integer: true, :greater_than_or_equal_to => 0}
  validates :latitude, presence: true, numericality: {:greater_than_or_equal_to => -180, :less_than_or_equal_to => 180}
  validates :longitude, presence: true, numericality: {:greater_than_or_equal_to => -180, :less_than_or_equal_to => 180}

  before_save { |lot| 
    lot.designation ||= DESIGNATION_ALL
  }

  before_save :set_enabled
  before_save :update_spot

  before_destroy :delete_spots

  def attributes
    super.merge('num_spots_available' => :num_spots_available)
  end

  def num_spots_available
    self.spots.where('status = ?', Spot::STATUS["AVAILABLE"]).count
  end

  def designation_to_s
    DESIGNATIONS[read_attribute(:designation)]
  end

  def self.to_csv(options = {})
    reports = all

    CSV.generate(options) do |csv|
      csv << column_names
      reports.each do |report|
        csv << report.attributes.values_at(*column_names)
      end
    end
  end

  private
    def set_enabled
     self.enabled = true if self.enabled.nil?
    end

    def update_spot
      if not self.spots.nil?
        if enabled
          self.spots.update_all(:status => Spot::STATUS["AVAILABLE"])
        else
          self.spots.update_all(:status => Spot::STATUS["UNAVAILABLE"])
        end
      end
    end

    def delete_spots
      self.spots.destroy_all
    end
end
