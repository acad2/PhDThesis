package io.crowbar.instrumentation;

import io.crowbar.instrumentation.runtime.Probe;

import java.io.Serializable;

public class Messages {
    public interface Message {}

    public static final class HelloMessage implements Message, Serializable {
        private static final long serialVersionUID = 4523313711692005000L;
        private final String id;

        public HelloMessage (String id) {
            this.id = id;
        }

        public String getId () {
            return id;
        }

        @Override
        public String toString () {
            return "[[HelloMessage], id: " + id + "]";
        }

        protected HelloMessage () {
            this(null);
        }
    }

    public static final class ByeMessage implements Message, Serializable {
        private static final long serialVersionUID = 3262507873284969712L;

        @Override
        public String toString () {
            return "[[ByeMessage]]";
        }
    }

    public static final class RegisterNodeMessage implements Message, Serializable {
        private static final long serialVersionUID = -1646717811451968968L;
        private final String name;
        private final int id;
        private final int parentId;

        public RegisterNodeMessage (String name, int id, int parentId) {
            this.name = name;
            this.id = id;
            this.parentId = parentId;
        }

        public String getName () {
            return name;
        }

        public int getId () {
            return id;
        }

        public int getParentId () {
            return parentId;
        }

        @Override
        public String toString () {
            String ret = "[[" + this.getClass().getSimpleName() + "]: ";


            ret += "name: " + name + "] ";
            ret += "id: " + id + "] ";
            ret += "parentId: " + parentId + "]";
            return ret;
        }

        protected RegisterNodeMessage () {
            this(null, -1, -1);
        }
    }

    public static final class RegisterProbeMessage implements Message, Serializable {
        private static final long serialVersionUID = 8746687288408031667L;
        private final Probe probe;
        public RegisterProbeMessage (Probe probe) {
            this.probe = new Probe(probe);
        }

        public Probe getProbe () {
            return probe;
        }

        @Override
        public String toString () {
            String ret = "[[" + this.getClass().getSimpleName() + "]: ";


            ret += probe + "]";
            return ret;
        }

        protected RegisterProbeMessage () {
            this(null); // FIXME: this code throws an exception
        }
    }

    public abstract static class ProbeMessage implements Message, Serializable {
        private static final long serialVersionUID = 2094257171874796426L;
        private final int probeId;

        public ProbeMessage (int probeId) {
            this.probeId = probeId;
        }

        public final int getProbeId () {
            return probeId;
        }

        @Override
        public String toString () {
            String ret = "[[" + this.getClass().getSimpleName() + "]: ";


            ret += "probe_id: " + probeId + "]";
            return ret;
        }

        protected ProbeMessage () {
            this(-1);
        }
    }

    public static final class TransactionStartMessage extends ProbeMessage implements Serializable {
        private static final long serialVersionUID = 7459099682879100695L;

        TransactionStartMessage (int probeId) {
            super(probeId);
        }

        protected TransactionStartMessage () {
            this(-1);
        }
    }

    public static final class TransactionEndMessage extends ProbeMessage implements Serializable {
        private static final long serialVersionUID = 5478029092609894069L;
        private final String exceptionClass;
        private final String exceptionMessage;
        private final boolean[] hitVector;


        TransactionEndMessage (int probeId,
                               String exceptionClass,
                               String exceptionMessage,
                               boolean[] hitVector) {
            super(probeId);
            this.exceptionClass = exceptionClass;
            this.exceptionMessage = exceptionMessage;
            this.hitVector = hitVector;
        }

        public String getExceptionClass () {
            return exceptionClass;
        }

        public String getExceptionMessage () {
            return exceptionMessage;
        }

        public boolean[] getHitVector () {
            return hitVector;
        }

        @Override
        public String toString () {
            String ret = "[[" + this.getClass().getSimpleName() + "]: ";


            ret += "probeId: " + getProbeId() + ", ";
            ret += "exceptionClass: " + getExceptionClass() + ", ";
            ret += "exceptionMessage: " + getExceptionMessage() + ", ";
            ret += "hitVector: " + getHitVector() + "]";
            return ret;
        }

        protected TransactionEndMessage () {
            this(-1, null, null, null);
        }
    }

    public static final class OracleMessage extends ProbeMessage implements Serializable  {
        private static final long serialVersionUID = -4795516008213040058L;
        private final double error;
        private final double confidence;

        OracleMessage (int probeId,
                       double error,
                       double confidence) {
            super(probeId);
            this.error = error;
            this.confidence = confidence;
        }

        public double getError () {
            return error;
        }

        public double getConfidence () {
            return confidence;
        }

        protected OracleMessage () {
            this(-1, -1, -1);
        }
    }
}