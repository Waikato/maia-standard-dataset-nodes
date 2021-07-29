package māia.topology.node.standard.ml.dataset

import māia.configure.Configurable
import māia.ml.dataset.DataRow
import māia.ml.dataset.DataStream
import māia.topology.NodeConfiguration
import māia.topology.node.base.Sink

/**
 * TODO: What class does.
 *
 * @author Corey Sterling (csterlin at waikato dot ac dot nz)
 */
class DatasetWriter : Sink<NodeConfiguration, DataStream<DataRow>> {

    @Configurable.Register<DatasetWriter, NodeConfiguration>(DatasetWriter::class, NodeConfiguration::class)
    constructor(block : NodeConfiguration.() -> Unit = {}) : super(block)

    override suspend fun consume(item: DataStream<DataRow>) {
        println("DatasetWriter received dataset: $item")
    }

}
