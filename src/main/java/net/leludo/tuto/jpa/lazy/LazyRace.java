package net.leludo.tuto.jpa.lazy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "race")
public class LazyRace
{

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToOne
    @JoinColumn(name = "championshipId", nullable = false)
    private LazyChampionship championship;

    public LazyRace(final LazyChampionship championship, final String name)
    {
        this.id = null;
        this.championship = championship;
        this.name = name;
    }

    public LazyRace()
    {

    }

    public Integer getId()
    {
        return id;
    }

    public String getName()
    {
        return this.name;
    }

    @Override
    public String toString()
    {
        return "GrandPrix [id=" + id + ", name=" + name + "]";
    }
}
