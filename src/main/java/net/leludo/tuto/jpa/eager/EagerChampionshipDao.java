package net.leludo.tuto.jpa.eager;

import java.util.List;

import net.leludo.tuto.jpa.DefaultDao;

public class EagerChampionshipDao extends DefaultDao<EagerChampionship, Integer> {

    public EagerChampionshipDao() {
        super(EagerChampionship.class);
    }

    public List<EagerChampionship> findAll() {
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
    public void create(final EagerChampionship championnat) {
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
    public void update(final EagerChampionship championnat) {
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
    public void delete(final EagerChampionship championnat) {
        this.em.getTransaction().begin();
        this.em.remove(championnat);
        this.em.getTransaction().commit();
        this.em.clear();
    }
}
