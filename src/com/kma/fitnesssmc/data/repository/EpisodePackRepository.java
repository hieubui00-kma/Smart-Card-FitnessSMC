package com.kma.fitnesssmc.data.repository;

import com.kma.fitnesssmc.data.local.FitnessDatabase;
import com.kma.fitnesssmc.data.model.EpisodePack;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EpisodePackRepository {
    private final FitnessDatabase database;

    public EpisodePackRepository(FitnessDatabase database) {
        this.database = database;
    }

    public @NotNull List<EpisodePack> getEpisodePacks() {
        List<EpisodePack> episodePacks = new ArrayList<>();
        String query = "SELECT * FROM episode_packs WHERE deleted_at IS NULL";

        try {
            ResultSet resultSet = database.executeQuery(query);
            EpisodePack episodePack;

            while (resultSet.next()) {
                episodePack = new EpisodePack();

                episodePack.setEpisodePackID(resultSet.getInt(1));
                episodePack.setName(resultSet.getString(2));
                episodePack.setPrice(resultSet.getLong(3));
                episodePack.setDuration(resultSet.getInt(4));
                episodePacks.add(episodePack);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return episodePacks;
    }
}
