package model

import model.enums.ColumnType
import org.junit.jupiter.api.Assertions.*   // ktlint-disable no-wildcard-imports no-multi-spaces
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

/**
 * Entity classes test
 */
internal class EntityTest {

    fun defaultTarget(): Entity {
        val c1 = EntityColumn("name1", "desc1", ColumnType.INT, true)
        val c2 = EntityColumn("name2", "desc2", ColumnType.VARCHAR, false)
        val c3 = EntityColumn("name3", "desc3", ColumnType.DATE, false)
        val cols = listOf(c1, c2, c3)
        val m1 = EntityColumn("meta1", "mDesc1", ColumnType.INT, true)
        val m2 = EntityColumn("meta2", "mDesc2", ColumnType.VARCHAR, false)
        val m3 = EntityColumn("meta3", "mDesc3", ColumnType.DATE, false)
        val metas = listOf(m1, m2, m3)
        return Entity("eName", "eDesc", "e.name.space", cols, metas)
    }

    @Nested
    inner class UniqueEnums {
        @Test
        fun hasOneEnum() {
            val target = defaultTarget()
            target.columns.first().enumClass = Enum("enum1", "desc11", "enum.pkg.a", emptyList())
            assertEquals(1, target.uniqueEnums().size)
        }

        @Test
        fun hasMultiUniqueEnums() {
            val target = defaultTarget()
            target.columns.first().enumClass = Enum("enum1", "desc11", "enum.pkg.a", emptyList())
            target.columns.last().enumClass = Enum("enum2", "desc22", "enum.pkg.b", emptyList())
            assertEquals(2, target.uniqueEnums().size)
        }

        @Test
        fun hasMultiNonUniqueEnums() {
            val target = defaultTarget()
            target.columns.first().enumClass = Enum("enum1", "desc11", "enum.pkg.a", emptyList())
            target.columns.last().enumClass = Enum("enum3", "desc33", "enum.pkg.a", emptyList())
            assertEquals(1, target.uniqueEnums().size)
        }

        @Test
        fun hasNoEnum() {
            val target = defaultTarget()
            assertEquals(0, target.uniqueEnums().size)
        }
    }

    @Nested
    inner class HasId {
        @Test
        fun hasOneId() {
            val target = defaultTarget()
            target.columns.first().pk = 1
            assertTrue(target.hasId())
        }

        @Test
        fun hasMultiId() {
            val target = defaultTarget()
            target.columns.first().pk = 1
            target.columns.last().pk = 2
            assertTrue(target.hasId())
        }

        @Test
        fun noId() {
            val target = defaultTarget()
            assertFalse(target.hasId())
        }
    }

    @Nested
    inner class HasLengthColumn {
        @Test
        fun hasOneLengthColumn() {
            val target = defaultTarget()
            target.columns.first().length = 128
            assertTrue(target.hasLengthColumn())
        }

        @Test
        fun hasMultiLengthColumn() {
            val target = defaultTarget()
            target.columns.first().length = 64
            target.columns.last().length = 256
            assertTrue(target.hasLengthColumn())
        }

        @Test
        fun hasOneLengthColumnAtMeta() {
            val target = defaultTarget()
            target.metaColumns.first().length = 128
            assertTrue(target.hasLengthColumn())
        }

        @Test
        fun noLengthColumn() {
            val target = defaultTarget()
            assertFalse(target.hasLengthColumn())
        }
    }
}

internal class EntityColumnTest {

    fun defaultTarget(): EntityColumn {
        return EntityColumn("name", "desc", ColumnType.INT, false)
    }

    @Nested
    inner class IsPk {
        @Test
        fun isPk() {
            val target = defaultTarget()
            target.pk = 1
            assertTrue(target.isPk())
        }

        @Test
        fun notPk() {
            val target = defaultTarget()
            target.pk = 0
            assertFalse(target.isPk())
        }
    }

    @Nested
    inner class HasLength {
        @Test
        fun hasLength() {
            val target = defaultTarget()
            target.length = 128
            assertTrue(target.hasLength())
        }

        @Test
        fun nullIsNotLength() {
            val target = defaultTarget()
            target.length = null
            assertFalse(target.hasLength())
        }

        @Test
        fun zeroLength() {
            val target = defaultTarget()
            target.length = 0
            assertFalse(target.hasLength())
        }
    }
}
