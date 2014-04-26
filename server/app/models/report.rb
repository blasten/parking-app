class Report < ActiveRecord::Base
  belongs_to :user
  belongs_to :lot
  has_one :reservation

  def self.to_csv(from, to, options = {})
    reports = where("date(created_at) >= ? and date(created_at) <= ?", "2014-04-26", "2014-04-26")

    CSV.generate(options) do |csv|
      csv << column_names
      reports.each do |report|
        csv << report.attributes.values_at(*column_names)
      end
    end
  end
end
