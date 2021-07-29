package māia.topology.node.standard.ml.dataset

import māia.configure.Configurable
import māia.configure.asReconfigureBlock
import māia.ml.dataset.DataRow
import māia.ml.dataset.view.DataRowBatchView
import māia.ml.dataset.view.viewAsDataBatch
import māia.topology.Node
import māia.topology.NodeConfiguration
import māia.topology.node.base.LockStepTransformer

/**
 * Forwards each row from a data-stream.
 */
@Node.WithMetadata("Forwards each row from a data-set")
class BatchViewRows : LockStepTransformer<BatchViewRowsConfiguration, DataRow, DataRowBatchView<*>> {

    @Configurable.Register<BatchViewRows, BatchViewRowsConfiguration>(
        BatchViewRows::class,
        BatchViewRowsConfiguration::class
    )
    constructor(block : BatchViewRowsConfiguration.() -> Unit) : super(block)

    constructor(configuration : BatchViewRowsConfiguration) : this(configuration.asReconfigureBlock())

    override suspend fun transformSingle(item : DataRow) : DataRowBatchView<*> {
        return item.viewAsDataBatch()
    }
}

class BatchViewRowsConfiguration : NodeConfiguration() {

}
