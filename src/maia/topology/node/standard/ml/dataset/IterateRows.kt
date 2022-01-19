package maia.topology.node.standard.ml.dataset

import maia.configure.Configurable
import maia.configure.asReconfigureBlock
import maia.ml.dataset.DataRow
import maia.ml.dataset.DataStream
import maia.topology.Node
import maia.topology.NodeConfiguration
import maia.topology.node.base.Transformer

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
