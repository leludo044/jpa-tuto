package net.leludo.tuto.jpa.lazy;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "championship")
public class LazyChampionship
{
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "championship")
    private List<LazyRace> races = new ArrayList<LazyRace>();

    public LazyChampionship(final String name)
    {
        this.name = name;
        this.races = new ArrayList<LazyRace>();
    }

    public List<LazyRace> getRaces()
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

    public LazyChampionship()
    {
        this.name = "Choose...";
        this.races = new ArrayList<LazyRace>();
    }

    public Integer getId()
    {
        return id;
    }

    public LazyRace organizeRace(final LazyRace race)
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
