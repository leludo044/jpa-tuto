package net.leludo.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper SQL fluide permettant de faciliter l'execution de requête SQL
 * unitaires.
 */
public final class SqlHelper
{
    /**
     * Logger for this class
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(SqlHelper.class);

    /**
     * Connexion SGBD nécessaire
     */
    private Connection con;

    /**
     * Précise quelle connexion SGBD utiliser
     * 
     * @param connection
     *            la connexion SGBD à utiliser
     * @return Le helper lui même
     */
    public SqlHelper with(final Connection connection)
    {
        this.con = connection;
        return this;
    }

    /**
     * Exécute une requête SQL unitaire
     * 
     * @param sql
     *            Le code de la requête SQL à exécuter
     * @return Le helper lui même
     * @throws SQLException
     *             Exception levée en cas d'erreur SQL
     */
    public SqlHelper executeSql(final String sql) throws SQLException
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("executeSql(String) - String sql=" + sql); //$NON-NLS-1$
        }

        PreparedStatement ps = null;
        try
        {
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (final SQLException e)
        {
            LOGGER.error(e.getMessage(), e); // $NON-NLS-1$
            throw e;
        } finally
        {
            try
            {
                if ((ps != null) && (!ps.isClosed()))
                {
                    ps.close();
                }
            } catch (final SQLException e)
            {
                LOGGER.error(e.getMessage(), e); // $NON-NLS-1$
            }
        }
        return this;
    }

    /**
     * Exécute une requête SQL unitaire
     * 
     * @param sql
     *            Le code de la requête SQL à exécuter
     * @return Le helper lui même
     * @throws SQLException
     *             Exception levée en cas d'erreur SQL
     */
    public Integer executeSqlUniqueResult(final String sql) throws SQLException
    {
        PreparedStatement ps = null;
        Integer uniqueValue = null;
        try
        {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                uniqueValue = rs.getInt(1);
            }
        } catch (final SQLException e)
        {
            LOGGER.error(e.getMessage(), e); // $NON-NLS-1$
            throw e;
        } finally
        {
            try
            {
                if ((ps != null) && (!ps.isClosed()))
                {
                    ps.close();
                }
            } catch (final SQLException e)
            {
                LOGGER.error(e.getMessage(), e); // $NON-NLS-1$
            }
        }
        return uniqueValue;
    }

    /**
     * Vérifie qu'une requête renvoie bien un nombre attendue de réponses
     * 
     * @param expectedCount
     *            Le nombre de réponses attendues
     * @param statement
     *            La requête à vérifier
     * @return true ou false
     * @throws SQLException
     *             Exception levée en cas d'erreur SQL
     */
    public boolean checkCount(final int expectedCount, final String statement)
        throws SQLException
    {
        return expectedCount == this.executeSqlUniqueResult(statement);
    }

}
