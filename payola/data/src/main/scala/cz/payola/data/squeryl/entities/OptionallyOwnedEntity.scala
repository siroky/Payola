package cz.payola.data.squeryl.entities

import cz.payola.data.squeryl.Entity

/**
 * Provides persistence to [[cz.payola.common.entities.OptionallyOwnedEntity]] entities.
 */
trait OptionallyOwnedEntity extends Entity with cz.payola.domain.entities.OptionallyOwnedEntity
{
    var ownerId = owner.map(_.id)

    /**
      * @param value New owner of entity
      */
    def owner_=(value: Option[User]) {
        ownerId = value.map(_.id)
        _owner = value
    }
}
