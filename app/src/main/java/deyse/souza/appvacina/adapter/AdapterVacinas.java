package deyse.souza.appvacina.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.model.Vacina;

public class AdapterVacinas extends RecyclerView.Adapter<AdapterVacinas.MyViewHolder> {

    private List<Vacina> vacinas;
    private Context context;
    public AdapterVacinas(List<Vacina> v,  Context context) {

        this.vacinas = v;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome;
        TextView dtvencimento;
        TextView dtaplicacao;
        TextView status;

        public MyViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textNomeVacina);
            dtvencimento = itemView.findViewById(R.id.textDtvenc);
            dtaplicacao = itemView.findViewById(R.id.textDtaplicacao);
            status = itemView.findViewById(R.id.textStatus);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_vacinas,parent, false);

        return new MyViewHolder(itemLista);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    Vacina vacina = vacinas.get(position);
    holder.nome.setText(vacina.getNome());
    holder.dtvencimento.setText(vacina.getDtvencimento());
    holder.dtaplicacao.setText(vacina.getDtaplicacao());
    holder.status.setText(vacina.getStatus());

    }

    @Override
    public int getItemCount() {
        return vacinas.size();
    }


}
