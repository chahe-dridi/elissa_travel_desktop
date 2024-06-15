    package com.example.pidev.services;

    import com.example.pidev.entities.Airport;
    import com.example.pidev.utils.MyDB;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

    public class AirportDAO implements IAirportDAO {

        private Connection conn;

        public AirportDAO() {
            conn = MyDB.getInstance().getCnx();
        }

        @Override
        public void addAirport(Airport airport) throws SQLException {
            String sql = "INSERT INTO airport (user_id, code, name, city, country) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, airport.getUserId());
                statement.setString(2, airport.getCode());
                statement.setString(3, airport.getName());
                statement.setString(4, airport.getCity());
                statement.setString(5, airport.getCountry());

                statement.executeUpdate();
            }
        }

        @Override
        public void updateAirport(Airport airport) throws SQLException {
            String sql = "UPDATE airport SET user_id=?, code=?, name=?, city=?, country=? WHERE id=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, airport.getUserId());
                statement.setString(2, airport.getCode());
                statement.setString(3, airport.getName());
                statement.setString(4, airport.getCity());
                statement.setString(5, airport.getCountry());
                statement.setInt(6, airport.getId());

                statement.executeUpdate();
            }
        }

        @Override
        public void deleteAirport(Airport airport) throws SQLException {
            String sql = "DELETE FROM airport WHERE id=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, airport.getId());
                statement.executeUpdate();
            }
        }


        @Override
        public List<Airport> getAllAirports() throws SQLException {
            List<Airport> airports = new ArrayList<>();
            String sql = "SELECT * FROM airport";
            try (PreparedStatement statement = conn.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Airport airport = new Airport(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("code"),
                            resultSet.getString("name"),
                            resultSet.getString("city"),
                            resultSet.getString("country")
                    );
                    airports.add(airport);
                }
            }
            return airports;
        }

        public Airport findById(int airportId) {
            Airport airport = null;
            String sql = "SELECT * FROM airport WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, airportId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        airport = new Airport();
                        airport.setId(resultSet.getInt("id"));
                        airport.setName(resultSet.getString("name"));
                        airport.setCity(resultSet.getString("city"));
                        airport.setCountry(resultSet.getString("country"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return airport;
        }



        @Override
        public List<Airport> searchAirportByCode(String code) throws SQLException {
            List<Airport> airports = new ArrayList<>();
            String sql = "SELECT * FROM airport WHERE code LIKE ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, "%" + code + "%");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Airport airport = new Airport(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("code"),
                                resultSet.getString("name"),
                                resultSet.getString("city"),
                                resultSet.getString("country")
                        );
                        airports.add(airport);
                    }
                }
            }
            return airports;
        }












        @Override
        public Airport getAirportById(int airportId) throws SQLException {
            Airport airport = null;
            String sql = "SELECT * FROM airport WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, airportId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        airport = new Airport(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("code"),
                                resultSet.getString("name"),
                                resultSet.getString("city"),
                                resultSet.getString("country")
                        );
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return airport;
        }
        public Airport getAirportByCode(String airportCode) throws SQLException {
            Airport airport = null;
            String sql = "SELECT * FROM airport WHERE code = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, airportCode);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        airport = new Airport(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("code"),
                                resultSet.getString("name"),
                                resultSet.getString("city"),
                                resultSet.getString("country")
                        );
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return airport;
        }

    }
