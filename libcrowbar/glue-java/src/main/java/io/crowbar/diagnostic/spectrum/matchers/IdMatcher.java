package io.crowbar.diagnostic.spectrum.matchers;

import io.crowbar.diagnostic.spectrum.Spectrum;
import java.util.BitSet;


/**
 * \brief This class matches probes/transactions by id.
 */
public final class IdMatcher
implements SpectrumMatcher {
    private final BitSet transactions = new BitSet();
    private final BitSet probes = new BitSet();

    public IdMatcher (BitSet transactions,
                      BitSet probes) {
        this.transactions.or(transactions);
        this.probes.or(probes);
    }

    public IdMatcher (int[] transactions,
                      int[] probes) {
        if (transactions != null)
            for (int t : transactions) {
                this.transactions.set(t);
            }

        if (probes != null)
            for (int c : probes) {
                this.probes.set(c);
            }
    }

    @Override
    public BitSet matchProbes (Spectrum spectrum) {
        return (BitSet) probes.clone();
    }

    @Override
    public BitSet matchTransactions (Spectrum spectrum) {
        return (BitSet) transactions.clone();
    }
}