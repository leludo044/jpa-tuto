package net.leludo.tuto.jpa.lazy;

import java.util.List;

import net.leludo.tuto.jpa.DefaultDao;

public class LazyChampionshipDao extends DefaultDao<LazyChampionship, Integer>
{

    public LazyChampionshipDao() {
        super(LazyChampionship.class);
    }

    public List<LazyChampionship> findAll()
    {
        String queryString = "from Championnat";
        javax.persistence.Query query = this.em.createQuery(queryString);
        return query.getResultList();
    }

    /**
     * Crée un championnat en base
     * 
     * @param championnat
     *            Le championnat à sauvegarder
     */
    public void create(final LazyChampionship championnat)
    {
        this.em.getTransaction().begin();
        this.em.persist(championnat);
        this.em.getTransaction().commit();
        this.em.clear();
    }

    /**
     * Modifie un championnat en base
     * 
     * @param championnat
     *            Le championnat à modifier
     */
    public void update(final LazyChampionship championnat)
    {
        this.em.getTransaction().begin();
        this.em.merge(championnat);
        this.em.getTransaction().commit();
        this.em.clear();
    }

    /**
     * Supprime un championnat en base
     * 
     * @param championnat
     *            Le championnat à supprimer
     */
    public void delete(final LazyChampionship championnat)
    {
        this.em.getTransaction().begin();
        this.em.remove(championnat);
        this.em.getTransaction().commit();
        this.em.clear();
    }
}
