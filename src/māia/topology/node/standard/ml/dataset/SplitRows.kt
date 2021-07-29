package māia.topology.node.standard.ml.dataset

import māia.configure.Configurable
import māia.ml.dataset.DataRow
import māia.ml.dataset.DataStream
import māia.topology.Node
import māia.topology.NodeConfiguration
import māia.topology.io.Input
import māia.topology.io.Output

/**
 * TODO: What class does.
 *
 * @author Corey Sterling (csterlin at waikato dot ac dot nz)
 */
class SplitRows : Node<NodeConfiguration> {

    @Configurable.Register<SplitRows, NodeConfiguration>(SplitRows::class, NodeConfiguration::class)
    constructor(block : NodeConfiguration.() -> Unit): super(block)

    val input by Input<DataStream<*>>()

    val output1 by Output<DataRow>()

    val output2 by Output<DataRow>()

    override suspend fun main() {
        val dataset = input.pull()
        input.close()
        var toggle = true
        for (row in dataset.rowIterator()) {
            if (toggle) output1.push(row) else output2.push(row)
            toggle = !toggle
        }
    }

}
