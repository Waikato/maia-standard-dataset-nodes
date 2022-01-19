package maia.topology.node.standard.ml.dataset

import maia.configure.Configurable
import maia.configure.asReconfigureBlock
import maia.ml.dataset.DataRow
import maia.ml.dataset.view.DataRowBatchView
import maia.ml.dataset.view.viewAsDataBatch
import maia.topology.Node
import maia.topology.NodeConfiguration
import maia.topology.node.base.LockStepTransformer

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
