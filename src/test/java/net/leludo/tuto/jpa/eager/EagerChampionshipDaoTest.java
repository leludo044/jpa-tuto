package net.leludo.tuto.jpa.eager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import net.leludo.tuto.jpa.CommonTest;

public class EagerChampionshipDaoTest extends CommonTest
{
    private EagerChampionshipDao acteur;

    @Before
    public void setUp() throws Exception
    {
        super.setUp();

        acteur = new EagerChampionshipDao();
        acteur.setEntityManager(emf);
    }

    @Test
    public final void createWithoutRace() throws Exception
    {
        EagerChampionship championship = new EagerChampionship("one");
        acteur.create(championship);
        assertTrue(
                sqlHelper.checkCount(1, "select count(*) from championship"));
        assertTrue(sqlHelper.checkCount(0, "select count(*) from race"));
    }

    @Test
    public final void createWithTwoRaces() throws Exception
    {
        EagerChampionship championship = new EagerChampionship("two");
        EagerRace race1 = new EagerRace(championship, "Silverstone");
        EagerRace race2 = new EagerRace(championship, "Spa");
        championship.organizeRace(race1);
        championship.organizeRace(race2);

        acteur.create(championship);

        assertTrue(
                sqlHelper.checkCount(1, "select count(*) from championship"));
        assertTrue(sqlHelper.checkCount(2, "select count(*) from race"));
    }

    @Test
    public final void updateWithOneMoreRace() throws Exception
    {
        this.createChampionshipWithTwoRaces();
        EagerChampionship championship = acteur.find(0);
        EagerRace race3 = new EagerRace(championship, "Monza");
        championship.organizeRace(race3);

        acteur.update(championship);

        assertTrue(
                sqlHelper.checkCount(1, "select count(*) from championship"));
        assertTrue(sqlHelper.checkCount(3, "select count(*) from race"));
    }

    @Test
    public final void updateWithOneLessRace() throws Exception
    {
        this.createChampionshipWithTwoRaces();
        EagerChampionship championship = acteur.find(0);
        championship.getRaces().remove(1);

        acteur.update(championship);

        assertTrue(
                sqlHelper.checkCount(1, "select count(*) from championship"));
        assertTrue(sqlHelper.checkCount(1, "select count(*) from race"));
    }

    @Test
    public final void updateWithOneMoreAndOneLessRace() throws Exception
    {
        this.createChampionshipWithTwoRaces();
        EagerChampionship championship = acteur.find(0);
        EagerRace race3 = new EagerRace(championship, "Monza");
        championship.organizeRace(race3);
        championship.getRaces().remove(1);

        acteur.update(championship);

        assertTrue(
                sqlHelper.checkCount(1, "select count(*) from championship"));
        assertTrue(sqlHelper.checkCount(2, "select count(*) from race"));
    }

    @Test
    public final void delete() throws Exception
    {
        EagerChampionship championship = new EagerChampionship("one");
        acteur.create(championship);
        EagerChampionship readChampionship = acteur.find(championship.getId());

        acteur.delete(readChampionship);

        assertTrue(
                sqlHelper.checkCount(0, "select count(*) from championship"));
    }

    @Test
    public final void list() throws Exception
    {
        this.createChampionshipWithTwoRaces();

        EagerChampionship championship = acteur.find(0);
        assertNotNull(championship);
        assertNotNull(championship.getRaces());
        assertEquals(2, championship.getRaces().size());
    }

    private void createChampionshipWithTwoRaces()
    {
        EagerChampionship championship = new EagerChampionship("two");
        EagerRace race1 = new EagerRace(championship, "Silverstone");
        EagerRace race2 = new EagerRace(championship, "Spa");
        championship.organizeRace(race1);
        championship.organizeRace(race2);

        acteur.create(championship);
    }
}
