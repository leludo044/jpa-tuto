package net.leludo.tuto.jpa.eager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "championship")
public class EagerChampionship
{
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EagerRace> races = new ArrayList<EagerRace>();

    public EagerChampionship(final String name)
    {
        this.name = name;
        this.races = new ArrayList<EagerRace>();
    }

    public List<EagerRace> getRaces()
    {
        return this.races;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public EagerChampionship()
    {
        this.name = "Choose...";
        this.races = new ArrayList<EagerRace>();
    }

    public Integer getId()
    {
        return id;
    }

    public EagerRace organizeRace(final EagerRace race)
    {
        this.races.add(race);
        return race;
    }

    @Override
    public String toString()
    {
        return "Championship [id=" + id + ", name=" + name + ", races=" + races
                + "]";
    }

}
