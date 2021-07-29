package māia.topology.node.standard.ml.dataset

import māia.configure.Configurable
import māia.configure.asReconfigureBlock
import māia.ml.dataset.DataRow
import māia.ml.dataset.DataStream
import māia.topology.Node
import māia.topology.NodeConfiguration
import māia.topology.node.base.Transformer

/**
 * Forwards each row from a data-stream.
 */
@Node.WithMetadata("Outputs each row of a data-set individually")
class IterateRows : Transformer<IterateRowsConfiguration, DataStream<DataRow>, DataRow> {

    @Configurable.Register<IterateRows, IterateRowsConfiguration>(
        IterateRows::class,
        IterateRowsConfiguration::class
    )
    constructor(block : IterateRowsConfiguration.() -> Unit) : super(block)

    constructor(configuration : IterateRowsConfiguration) : this(configuration.asReconfigureBlock())

    override suspend fun transform(item: DataStream<DataRow>): Iterator<DataRow> {
        return item.rowIterator()
    }
}

class IterateRowsConfiguration : NodeConfiguration() {

}
