package net.leludo.tuto.jpa.lazy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.hibernate.cfg.NotYetImplementedException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import net.leludo.tuto.jpa.CommonTest;

public class LazyChampionshipDaoTest extends CommonTest
{
    private LazyChampionshipDao acteur;

    @Before
    public void setUp() throws Exception
    {
        super.setUp();

        acteur = new LazyChampionshipDao();
        acteur.setEntityManager(emf);
    }

    @Test
    public final void createWithoutRace() throws Exception
    {
        LazyChampionship championship = new LazyChampionship("one");
        acteur.create(championship);
        assertTrue(
                sqlHelper.checkCount(1, "select count(*) from championship"));
        assertTrue(sqlHelper.checkCount(0, "select count(*) from race"));
    }

    @Test
    public final void createWithTwoRaces() throws Exception
    {
        LazyChampionship championship = new LazyChampionship("two");
        LazyRace race1 = new LazyRace(championship, "Silverstone");
        LazyRace race2 = new LazyRace(championship, "Spa");
        championship.organizeRace(race1);
        championship.organizeRace(race2);

        acteur.create(championship);

        assertTrue(
                sqlHelper.checkCount(1, "select count(*) from championship"));
        assertTrue(sqlHelper.checkCount(0, "select count(*) from race"));
    }

    @Test
    public final void updateWithOneMoreRace() throws Exception
    {
        this.createChampionshipWithTwoRaces();
        LazyChampionship championship = acteur.find(0);
        LazyRace race3 = new LazyRace(championship, "Monza");
        championship.organizeRace(race3);

        acteur.update(championship);

        assertTrue(
                sqlHelper.checkCount(1, "select count(*) from championship"));
        assertTrue(sqlHelper.checkCount(0, "select count(*) from race"));
    }

    @Test
    @Ignore
    public final void updateWithOneLessRace() throws Exception
    {
        throw new NotYetImplementedException();
    }

    @Test
    @Ignore
    public final void updateWithOneMoreAndOneLessRace() throws Exception
    {
        throw new NotYetImplementedException();
    }

    @Test
    public final void delete() throws Exception
    {
        LazyChampionship championship = new LazyChampionship("one");
        acteur.create(championship);
        LazyChampionship readChampionship = acteur.find(championship.getId());

        acteur.delete(readChampionship);

        assertTrue(
                sqlHelper.checkCount(0, "select count(*) from championship"));
    }

    @Test
    public final void list() throws Exception
    {
        this.createChampionshipWithTwoRaces();

        LazyChampionship championship = acteur.find(0);
        assertNotNull(championship);
        assertNotNull(championship.getRaces());
        assertEquals(0, championship.getRaces().size());
    }

    private void createChampionshipWithTwoRaces()
    {
        LazyChampionship championship = new LazyChampionship("two");
        LazyRace race1 = new LazyRace(championship, "Silverstone");
        LazyRace race2 = new LazyRace(championship, "Spa");
        championship.organizeRace(race1);
        championship.organizeRace(race2);

        acteur.create(championship);
    }
}
