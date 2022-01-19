package maia.topology.node.standard.ml.dataset

import maia.configure.Configurable
import maia.configure.ConfigurationItem
import maia.ml.dataset.DataStream
import maia.ml.dataset.view.DataStreamView
import maia.topology.NodeConfiguration
import maia.topology.node.base.LockStepTransformer
import maia.util.filter

/**
 * TODO: What class does.
 *
 * @author Corey Sterling (csterlin at waikato dot ac dot nz)
 */
class Remove : LockStepTransformer<RemoveConfiguration, DataStream<*>, DataStream<*>> {

    @Configurable.Register<Remove, RemoveConfiguration>(Remove::class, RemoveConfiguration::class)
    constructor(block : RemoveConfiguration.() -> Unit = {}) : super(block)

    override suspend fun transformSingle(item: DataStream<*>): DataStream<*> {
        return DataStreamView(
                item,
                (0 until item.numColumns).iterator().filter { it != configuration.index }
        )
    }
}

class RemoveConfiguration : NodeConfiguration("remove") {

    var index by ConfigurationItem { -1 }

}
