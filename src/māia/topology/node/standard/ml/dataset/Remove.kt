package māia.topology.node.standard.ml.dataset

import māia.configure.Configurable
import māia.configure.ConfigurationItem
import māia.ml.dataset.DataStream
import māia.ml.dataset.view.DataStreamView
import māia.topology.NodeConfiguration
import māia.topology.node.base.LockStepTransformer
import māia.util.filter

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
