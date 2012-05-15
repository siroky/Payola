package cz.payola.model

import cz.payola.data.entities.User
import cz.payola.data.entities.dao.{AnalysisDAO, UserDAO}
import cz.payola.domain.rdf.Graph

class DataFacade
{
    val userDAO = new UserDAO
    val analysisDAO = new AnalysisDAO

    def getUserByCredentials(username: String, password: String) : Option[User] = {
        userDAO.getUserByCredentials(username, cryptPassword(password))
    }

    def getUserByUsername(username: String) : Option[User] = {
        userDAO.getUserByUsername(username)
    }

    def register(username: String, password: String): Unit = {
        val u = new User(username, cryptPassword(password), username)

        userDAO.persist(u)
    }

    def getPublicAnalysesByOwner(o: User) = {
        analysisDAO.getPublicAnalysesByOwner(o)
    }

    def getGraph(uri: String) : Graph = {
        null
    }

    //TODO bcrypt
    private def cryptPassword(password: String, method: String = "SHA-1") : String = {
        val md = java.security.MessageDigest.getInstance(method)
        val digest = md.digest(password.toCharArray.map(_.toByte))
        new String(digest.map(_.toChar))
    }
}
