package cz.payola.web.shared.managers

import s2js.compiler._
import cz.payola.web.shared.Payola
import cz.payola.domain.entities._
import cz.payola.domain.entities.plugins.concrete.DataFetcher

@remote @secured object PluginManager
    extends ShareableEntityManager[Plugin, cz.payola.common.entities.Plugin](Payola.model.pluginModel)
{
    @secured @async def getAccessibleDataFetchers(user: Option[User] = null)
        (successCallback: Seq[cz.payola.common.entities.Plugin] => Unit)
        (errorCallback: Throwable => Unit) {

        successCallback(model.getAccessibleToUser(user).filter(_.isInstanceOf[DataFetcher]))
    }

    /** Attempts to create a new plugin from pluginCode.
      *
      * @throws Exception when an error occurs (compilation, already-existing name, ...).
      *                   Details in exception's message.
      * @param pluginCode Code of the plugin.
      * @param user User.
      * @param successCallback Success callback.
      * @param failCallback Fail callback.
      */
    @async def uploadPlugin(pluginCode: String, user: User = null)(successCallback: (String => Unit))
        (failCallback: (Throwable => Unit)) {
        // Sanity check
        assert(user != null, "Not logged in, or some other error")

        // Try to compile code
        try {
            val plugin = Payola.model.pluginModel.createPluginFromSource(pluginCode, user)
            if (plugin != null) {
                user.addOwnedPlugin(plugin)
                Payola.model.userModel.persist(user)
                successCallback("Plugin saved.")
            }
        } catch {
            case e: Exception => {
                e.printStackTrace()
                failCallback(new Exception("Couldn't save plugin.\n\nDetails: " + e.getMessage))
            }
        }
    }
}