package maia.topology.node.standard.ml.dataset

import maia.configure.Configurable
import maia.configure.asReconfigureBlock
import maia.ml.dataset.WithColumns
import maia.ml.dataset.headers.DataColumnHeadersView
import maia.ml.dataset.view.readOnlyView
import maia.topology.ExecutionState
import maia.topology.Node
import maia.topology.NodeConfiguration
import maia.topology.io.Output
import maia.topology.io.Throughput
import maia.topology.node.base.LockStepTransformer

/**
 * Utility node which initialises a learner on the first data-dataset
 * to pass through it.
 *
 * @author Corey Sterling (csterlin at waikato dot ac dot nz)
 */
@Node.WithMetadata("Outputs the headers of the first item seen")
class InitialiseOnFirst<D : WithColumns> : LockStepTransformer<InitialiseOnFirstConfiguration, D, D> {

    @Configurable.Register<InitialiseOnFirst<*>, InitialiseOnFirstConfiguration>(
        InitialiseOnFirst::class, InitialiseOnFirstConfiguration::class)
    constructor(block : InitialiseOnFirstConfiguration.() -> Unit) : super(block)

    constructor(configuration : InitialiseOnFirstConfiguration) : this(configuration.asReconfigureBlock())

    @Throughput.WithMetadata("Outputs the initialisation headers for a learner")
    val initialise by Output<WithColumns>()

    /** Whether the item is the first seen. */
    private var first by ExecutionState { true }

    override suspend fun transformSingle(item: D): D {
        initialiseOnFirst(item)
        return item
    }

    private suspend fun initialiseOnFirst(item : D) {
        if (first) {
            doAsync {
                initialise.push(item.headers)
                initialise.close()
            }
            first = false
        }
    }

}

class InitialiseOnFirstConfiguration : NodeConfiguration("initialiseOnFirst")
