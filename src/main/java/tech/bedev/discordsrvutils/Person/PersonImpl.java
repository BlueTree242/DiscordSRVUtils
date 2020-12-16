package tech.bedev.discordsrvutils.Person;



import github.scarsz.discordsrv.dependencies.jda.api.entities.Member;
import org.bukkit.Bukkit;
import tech.bedev.discordsrvutils.DiscordSRVUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

    public class PersonImpl implements Person {

    private final UUID uuid;
    private final Member DiscordUser;
    private DiscordSRVUtils core;

    public PersonImpl(UUID uuid, Member DiscordUser, DiscordSRVUtils core) {
        this.DiscordUser = DiscordUser;
        this.core = core;
        if (uuid != null) {
            this.uuid = Bukkit.getOfflinePlayer(uuid).getUniqueId();
        } else {
            this.uuid = null;
        }
    }
    @Override
    public void setLevel(int level) {
        try (Connection conn = core.getDatabaseFile()) {
            if (uuid != null) {
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE unique_id=?");
                p1.setString(1, uuid.toString());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET level=? WHERE unique_id=?");
                    p2.setInt(1, level);
                    p2.setString(2, uuid.toString());
                    p2.execute();
                }
            } else {
                if (DiscordUser == null) return;
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE userID=?");
                p1.setLong(1, DiscordUser.getIdLong());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET level=? WHERE userID=?");
                    p2.setInt(1, level);
                    p2.setLong(2, DiscordUser.getIdLong());
                    p2.execute();
                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void addLevels(int levels) {
        try (Connection conn = core.getDatabaseFile()) {
            if (uuid != null) {
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE unique_id=?");
                p1.setString(1, uuid.toString());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET level=? WHERE unique_id=?");
                    p2.setInt(1, r1.getInt("level") + levels);
                    p2.setString(2, uuid.toString());
                    p2.execute();
                }
            } else {
                if (DiscordUser == null) return;
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE userID=?");
                p1.setLong(1, DiscordUser.getIdLong());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET level=? WHERE userID=?");
                    p2.setInt(1, r1.getInt("level") + levels);
                    p2.setLong(2, DiscordUser.getIdLong());
                    p2.execute();
                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeLevels(int levels) {
        try (Connection conn = core.getDatabaseFile()) {
            if (uuid != null) {
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE unique_id=?");
                p1.setString(1, uuid.toString());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET level=? WHERE unique_id=?");
                    p2.setInt(1, r1.getInt("level") - levels);
                    p2.setString(2, uuid.toString());
                    p2.execute();
                }
            } else {
                if (DiscordUser == null) return;
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE userID=?");
                p1.setLong(1, DiscordUser.getIdLong());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET level=? WHERE userID=?");
                    p2.setInt(1, r1.getInt("level") - levels);
                    p2.setLong(2, DiscordUser.getIdLong());
                    p2.execute();
                }

            }

    } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void clearLevels() {
        try (Connection conn = core.getDatabaseFile()) {
            if (uuid != null) {
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE unique_id=?");
                p1.setString(1, uuid.toString());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET level=0 WHERE unique_id=?");
                    p2.setString(1, uuid.toString());
                    p2.execute();
                }
            } else {
                if (DiscordUser == null) return;
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE userID=?");
                p1.setLong(1, DiscordUser.getIdLong());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET level=0 WHERE userID=?");
                    p2.setLong(1, DiscordUser.getIdLong());
                    p2.execute();
                }
            }
    } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void setXP(int xp) {
        try (Connection conn = core.getDatabaseFile()) {
            if (uuid != null) {
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE unique_id=?");
                p1.setString(1, uuid.toString());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET XP=? WHERE unique_id=?");
                    p2.setInt(1, xp);
                    p2.setString(2, uuid.toString());
                    p2.execute();
                }
            } else {
                if (DiscordUser == null) return;
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE userID=?");
                p1.setLong(1, DiscordUser.getIdLong());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET XP=? WHERE userID=?");
                    p2.setInt(2, xp);
                    p2.setLong(2, DiscordUser.getIdLong());
                    p2.execute();
                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void addXP(int xp) {
        try (Connection conn = core.getDatabaseFile()) {
            if (uuid != null) {
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE unique_id=?");
                p1.setString(1, uuid.toString());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET XP=? WHERE unique_id=?");
                    p2.setInt(1, r1.getInt("XP") + xp);
                    p2.setString(2, uuid.toString());
                    p2.execute();
                }
            } else {
                if (DiscordUser == null) return;
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE userID=?");
                p1.setLong(1, DiscordUser.getIdLong());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET XP=? WHERE userID=?");
                    p2.setInt(1, r1.getInt("XP") + xp);
                    p2.setLong(2, DiscordUser.getIdLong());
                    p2.execute();
                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void removeXP(int xp) {
        try (Connection conn = core.getDatabaseFile()) {
            if (uuid != null) {
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE unique_id=?");
                p1.setString(1, uuid.toString());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET XP=? WHERE unique_id=?");
                    p2.setInt(1, r1.getInt("XP") - xp);
                    p2.setString(2, uuid.toString());
                    p2.execute();
                }
            } else {
                if (DiscordUser == null) return;
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE userID=?");
                p1.setLong(1, DiscordUser.getIdLong());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET XP=? WHERE userID=?");
                    p2.setInt(1, r1.getInt("XP") - xp);
                    p2.setLong(2, DiscordUser.getIdLong());
                    p2.execute();
                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void clearXP() {
        try (Connection conn = core.getDatabaseFile()) {
            if (uuid != null) {
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE unique_id=?");
                p1.setString(1, uuid.toString());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET XP=0 WHERE unique_id=?");
                    p2.setString(1, uuid.toString());
                    p2.execute();
                }
            } else {
                if (DiscordUser == null) return;
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE userID=?");
                p1.setLong(1, DiscordUser.getIdLong());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    PreparedStatement p2 = conn.prepareStatement("UPDATE discordsrvutils_leveling SET XP=0 WHERE userID=?");
                    p2.setLong(1, DiscordUser.getIdLong());
                    p2.execute();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public int getLevel() {
        if (uuid != null) {
            try (Connection conn = core.getDatabaseFile()) {
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE unique_id=?");
                p1.setString(1, uuid.toString());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    return r1.getInt("level");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    public int getXP() {
        if (uuid != null) {
            try (Connection conn = core.getDatabaseFile()) {
                PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE unique_id=?");
                p1.setString(1, uuid.toString());
                p1.execute();
                ResultSet r1 = p1.executeQuery();
                if (r1.next()) {
                    return r1.getInt("XP");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return -1;
    }

        @Override
        public boolean isLinked() {
            int count = 0;
            if (uuid != null) count++;
            if (DiscordUser != null) count++;
            if (count == 2) return true;
            return false;
        }

        @Override
    public UUID getMinecraftUUID() {
        if (this.uuid == null) return null;
        return this.uuid;
    }

    @Override
    public Member getMemberOnMainGuild() {
        if (this.DiscordUser == null) return null;
        return this.DiscordUser;
    }

        @Override
        public void insertLeveling() {
                try (Connection conn = core.getDatabaseFile()) {
                    if (uuid != null) {
                        PreparedStatement p1 = conn.prepareStatement("SELECT * FROM discordsrvutils_leveling WHERE unique_id=?");
                        p1.setString(1, uuid.toString());
                        p1.execute();
                        ResultSet r1 = p1.executeQuery();
                        if (!r1.next()) {
                            if (DiscordUser == null) {
                                PreparedStatement p2 = conn.prepareStatement("INSERT INTO discordsrvutils_leveling (unique_id, XP, level) VALUES (?, 0, 0)");
                                p2.setString(1, uuid.toString());
                                p2.execute();
                            } else {
                                PreparedStatement p2 = conn.prepareStatement("INSERT INTO discordsrvutils_leveling (unique_id, XP, level, userID) VALUES (?, 0, 0, ?)");
                                p2.setString(1, uuid.toString());
                                p2.setLong(2, DiscordUser.getIdLong());
                                p2.execute();

                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

    }